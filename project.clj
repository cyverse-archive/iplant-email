(defproject iplant-email "1.0.0-SNAPSHOT"
  :description "iPlant Email Service"
  :dependencies [[org.clojure/clojure "1.3.0"]
                 [org.iplantc/clojure-commons "1.2.0-SNAPSHOT"]
                 [org.clojure/data.json "0.1.1"]
                 [org.clojure/tools.logging "0.2.3"]
                 [javax.mail/mail "1.4"]
                 [org.bituf/clj-stringtemplate "0.2"]
                 [compojure "1.0.1"]
                 [ring/ring-jetty-adapter "1.0.1"]
                 [log4j/log4j "1.2.16"]]
  :dev-dependencies [[org.iplantc/lein-iplant-rpm "1.1.1-SNAPSHOT"]]
  :iplant-rpm {:summary "iplant-email"
               :release 2
               :dependencies ["iplant-service-config >= 0.1.0-5"]
               :config-files ["log4j.properties"]
               :config-path "conf"
               :resources ["*.st"]}
  :aot [iplant-email.core]
  :main iplant-email.core)
