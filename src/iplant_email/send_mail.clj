(ns iplant-email.send-mail
  (:import [javax.mail Session Message Transport Message$RecipientType]
           [javax.mail.internet InternetAddress MimeMessage]))

(defn set-props
  [host]
  (let [props (System/getProperties)]
    (. props put "mail.smtp.host" host)
    props))

(defn send-email
  [{:keys [host to-addr from-addr from-name subject body]}]
  (let [props   (set-props host)
        session (Session/getDefaultInstance props nil)
        msg     (MimeMessage. session)
        from    (InternetAddress. from-addr)
        to      (InternetAddress. to-addr)]
    (when from-name
      (.setPersonal from from-name))
    (doto msg
      (.setFrom from)
      (.addRecipient Message$RecipientType/TO to)
      (.setSubject subject)
      (.setText body))
    (Transport/send msg)))
