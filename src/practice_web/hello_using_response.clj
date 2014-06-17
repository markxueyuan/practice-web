(ns practice-web.hello-using-response
  (:use ring.adapter.jetty)
  (:require [ring.util.response :as rp]))

(defn handler
  [request]
  (-> (rp/response "<a href=\"http://www.sohu.com\">干你老母！</a><p>么么哒</p>")
      (rp/content-type "text/html")
      (rp/charset "utf-8")))

(def server (run-jetty #'handler {:port 8080 :join? false}))

(.stop server)

