(ns iplant-email.core
  (:use compojure.core)
  (:use [compojure.route :as route]
        [compojure.handler :as handler]
        [clojure.data.json :as json]
        [clojure.tools.logging :as log]
        [clojure-commons.props :as props]
        [clojure-commons.clavin-client :as cl]
        [iplant-email.send-mail :as sm]
        [iplant-email.json-body :as jb]
        [iplant-email.json-validator :as jv]
        [iplant-email.templatize :as tmpl]))

(def zkprops (props/parse-properties "iplant-email.properties"))
(def zkurl (get zkprops "zookeeper"))
(def config (atom nil))

(cl/with-zk
  zkurl
  (when (not (cl/can-run?))
    (log/warn "THIS APPLICATION CANNOT RUN ON THIS MACHINE. SO SAYETH ZOOKEEPER.")
    (log/warn "THIS APPLICATION WILL NOT EXECUTE CORRECTLY."))
  
  (reset! config (cl/properties "iplant-email")))

(def smtp-host (get @config "iplant-email.smtp.host"))
(def smtp-from-addr (get @config "iplant-email.smtp.from-address"))

(defn format-exception
  "Formats a raised exception as a JSON object. Returns a response map."
  [exception]
  (log/debug "format-exception")
  (let [string-writer (java.io.StringWriter.)
        print-writer  (java.io.PrintWriter. string-writer)]
    (. exception printStackTrace print-writer)
    (let [localized-message (. exception getLocalizedMessage)
          stack-trace       (. string-writer toString)]
      (log/warn (str localized-message stack-trace))
      {:status 500
       :body (json/json-str {:message     localized-message
                             :stack-trace stack-trace})})))

(defroutes email-routes
  (GET "/" [] "Welcome to iPlant Email!")
  
  (POST "/" {body :body}
        (log/debug (str "Received request with body: " (json/json-str body)))
        (cond
          (not (jv/valid? body {:template string?}))
          {:status 500 :body body}
          
          :else
          (try
            (let [template-name   (:template body)
                  template-values (:values body)
                  to-addr         (:to body)
                  subject         (:subject body)
                  from-addr       smtp-from-addr
                  email-body      (tmpl/create-email template-name template-values)
                  send-return     (sm/send-email smtp-host to-addr from-addr subject email-body)]
              (log/debug (str "Successfully sent email for request: " (json/json-str body)))
              {:status 200 :body "Email sent."})
            (catch Exception e
              (format-exception e))))))

(defn site-handler [routes]
  (-> routes
    jb/parse-json-body))

(def app
  (site-handler email-routes))
