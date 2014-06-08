(ns practice-web.core
  (:use [ring.adapter.jetty :only (run-jetty)]
        [ring.middleware.params :only (wrap-params)]
        [ring.middleware cookies session]
        [compojure.core :only [GET POST PUT defroutes]])
  (:require [ring.util.response :as response]
            [compojure.route :as route]))

(defn app
  [{:keys [uri]}]
  {:body (format "You requested %s" uri)})

(def server (run-jetty #'app {:port 8080 :join? false}))

;(.stop server)


(defn app*
  [{:keys [uri params]}]
  {:body (format "You requested %s with query %s" uri params)})

(def app (wrap-params app*))

(defn my-app
  [request]
  {:status 200
   :headers {"Content-type" "text/html"}
   :body (format "<html><body style='font-family:cursive'>You requested: %s</body></html>" (:uri request))})

(defn wrap-logger
  [handler]
  (fn [request]
    (println (:uri request))
    (handler request)))

(def my-app (-> my-app
                wrap-cookies
                wrap-session
                wrap-logger))


(def app my-app)

(def ^:private counter (atom 0))

(def ^:private mappings (ref {}))

(defn url-for
  [id]
  (@mappings id))

(defn shorten!
  ([url]
   (let [id (swap! counter inc)
         id (Long/toString id 36)]
     (or (shorten! url id)
         (recur url))))
  ([url id]
   (dosync
    (when-not (@mappings id)
      (alter mappings assoc id url)
      id))))

(shorten! "http://clojurebook.com")

(shorten! "http://clojure.org" "clj")

(shorten! "http://gaximada" "clj")

@mappings

(defn retain
  [& [url id :as args]]
  (if-let [id (apply shorten! args)]
    {:status 201
     :headers {"Location" id}
     :body (list "URL " url " assigned the short identifier " id)}
    {:status 409
     :body (format "Short URL %s is already taken" id)}))

(defn redirect
  [id]
  (if-let [url (url-for id)]
    (response/redirect url)
    {:status 404 :body (str "No such short URL: " id)}))


(defroutes app*
  (GET "/" request "Welcome!")
  (PUT "/:id" [id url] (retain url id))
  (POST "/" [url] (retain url))
  (GET "/:id" [id] (redirect id))
  (GET "/list/" [] (interpose "\n" (keys @mappings)))
  (route/not-found "Sorry, there is nothing here."))


((PUT "/:id"
      [id url]
      (list "You requested that " url " be assigned ID " id))
 {:uri "/heheid" :params {:url "http://www.sohu.com"} :request-method :put})

((PUT ["/*/*/:id/:id"] [* id] (str * id))
 {:uri "/abc/xyz/foo/bar" :request-method :put})






















