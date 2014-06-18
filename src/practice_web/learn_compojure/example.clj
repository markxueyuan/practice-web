(ns practice-web.learn-compojure.example
  (:use compojure.core
        practice-web.learn-compojure.views
        [hiccup.middleware :only (wrap-base-url)]
        ring.adapter.jetty)
  (:require [compojure.route :as route]
            [compojure.handler :as handler]
            [compojure.response :as response]
            [ring.mock.request :as mock]))

(defroutes main-routes
  (GET "/" [] (index-page))
  (route/resources "/")
  (route/not-found "404 page not found"))


(def app
  (-> (handler/site main-routes)
      (wrap-base-url)))

(def server (run-jetty #'app {:port 6060 :join? false}))

(.stop server)

