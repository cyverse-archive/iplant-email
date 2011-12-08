(ns iplant-email.templatize
  (:require [org.bituf.clj-stringtemplate :as stt]))

(defn create-email
  [template-name value-map]
  (stt/render-view 
    (stt/fill-view! 
      (stt/get-view-from-classpath template-name)
      value-map)))

