(ns practice-web.hello-world
  (:use ring.adapter.jetty)
  (:require [ring.util.response :as rp]))


(defn handler
  [request]
  (let [basic {:status 200
               :headers {"Content-Type" "text/plain"}
               :body "干你老母！"}]
    (rp/charset basic "utf-8")))

(handler "")

(def server (run-jetty #'handler {:port 7070 :join? false}))

(.stop server)
