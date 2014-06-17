(ns ring.example.session
  (:use ring.middleware.session
        ring.util.response
        ring.adapter.jetty))

(defn handler [{session :session, uri :uri}]
  (let [n (session :n 1)]
    (if (= uri "/")
      (-> (response (str "You have visited " n " times"))
          (content-type "text/plain")
          (assoc-in [:session :n] (inc n)))
      (-> (response "Page not found")
          (status 404)))))

(def app
  (-> handler wrap-session))

(def server (run-jetty #'app {:port 1010 :join? false}))

;(.stop server)
