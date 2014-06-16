(ns practice-web.hello-world
  (:use ring.adapter.jetty
        [ring.middleware.file]
        [ring.middleware.resource]
        [ring.middleware.content-type]
        [ring.middleware.not-modified]
        [ring.middleware.params]
        )
  (:require [ring.util.response :as rp]))

(defn handler
  [request]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body "Hello World"})

;(run-jetty handler {:port 2929})
;(handler {:server-port 2929 :server-name "localhost"})

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;midware;;;;;;;;;;;;;;;;;;;;;;;

(defn wrap-content-type-diy
  [handler content-type]
  (fn [request]
    (let [response (handler request)]
      (assoc-in response [:headers "Content-Type"] content-type))))

(def app (wrap-content-type handler "text/html"))

(app "")

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;response;;;;;;;;;;;;;;;;;;;;;

;general response

(-> (rp/response "Hello World")
    (rp/content-type "text/plain"))

(rp/response "Hello World")

(rp/content-type {} "text/plain")

;special redirect function

(rp/redirect "http://www.sohu.com")

;file should be from local filesystem

(rp/file-response "readme.html" {:root "D:/data/"})

;resource should be from the classpath

(rp/resource-response "readme.html" {:root "practice_web"})

(defn disc-handler
  [request]
  (rp/file-response "readme.html" {:root "D:/data/"}))

(defn classpath-handler
  [request]
  (rp/resource-response "readme.html" {:root "practice_web"}))

(classpath-handler "")

(defn normal-handler
  [request]
  (rp/response "hello world"))

(normal-handler "")

(defonce server (run-jetty disc-handler {:port 9090 :join? false}))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;static resources;;;;;;;;;;;;;;;;;;;;;;;;;

;wrap-file is a middleware function

(def app (wrap-file disc-handler "D:/data"))

(app "")

;wrap-resource is a middleware function

(def app2 (wrap-resource classpath-handler "resources/public"))

(def app3
  (-> classpath-handler
      (wrap-resource "resources/")
      ;(wrap-content-type)
      (wrap-not-modified)
      ))

(app3 "")

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;content types;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(def app4
  (wrap-content-type classpath-handler))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;parameters;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(def app5
  (wrap-params normal-handler))












(def test-handler
  (-> normal-handler
      ;(wrap-resource "practice_web/readme.html")
      (wrap-content-type)))
























