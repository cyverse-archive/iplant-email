(defproject iplant-email "1.0.0-SNAPSHOT"
  :description "FIXME: write description"
  :dependencies [[org.clojure/clojure "1.2.1"]
                 [org.clojure/clojure-contrib "1.2.0"]
                 [org.iplantc/clojure-commons "1.0.0-SNAPSHOT"]
                 [javax.mail/mail "1.4"]
                 [org.bituf/clj-stringtemplate "0.2"]
                 [compojure "0.6.5"]
                 [log4j/log4j "1.2.16"]]
  :dev-dependencies [[lein-ring "0.4.5"]]
  :ring {:handler iplant-email.core/app})