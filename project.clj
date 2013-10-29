(defproject iplant-email "1.2.1-SNAPSHOT"
  :description "iPlant Email Service"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.iplantc/clojure-commons "1.4.1-SNAPSHOT"]
                 [org.clojure/tools.logging "0.2.3"]
                 [cheshire "5.0.1"]
                 [javax.mail/mail "1.4"]
                 [org.bituf/clj-stringtemplate "0.2"]
                 [compojure "1.0.1"]
                 [ring/ring-jetty-adapter "1.0.1"]
                 [log4j/log4j "1.2.16"]]
  :plugins [[org.iplantc/lein-iplant-rpm "1.4.1-SNAPSHOT"]]
  :iplant-rpm {:summary "iplant-email"
               :dependencies ["iplant-service-config >= 0.1.0-5" "iplant-clavin" "java-1.7.0-openjdk"]
               :config-files ["log4j.properties"]
               :config-path "conf"
               :resources ["*.st"]}
  :repositories  {"iplantCollaborative"
                  "http://projects.iplantcollaborative.org/archiva/repository/internal/"}
  :aot [iplant-email.core]
  :main iplant-email.core)
