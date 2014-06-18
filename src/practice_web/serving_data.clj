(ns practice-web.serving-data
  (:require [compojure.route :as route]
            [compojure.handler :as handler]
            [clojure.string :as str])
  (:use compojure.core
        ring.adapter.jetty
        [ring.middleware.content-type :only (wrap-content-type)]
        [ring.middleware.file :only (wrap-file)]
        [ring.middleware.file-info :only (wrap-file-info)]
        [ring.middleware.stacktrace :only (wrap-stacktrace)]
        [ring.util.response :only (redirect)]))



(defroutes site-routes
  (GET "/" [] (redirect "public/data/census-race.json"))
  (route/resources "/")
  (route/not-found "Page not found"))

(def app
  (-> (handler/site site-routes)
      (wrap-file "resources")
      (wrap-file-info)
      (wrap-content-type)))

(def server (run-jetty #'app {:port 5050 :join? false}))

(.stop server)

