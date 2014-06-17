(ns practice-web.form-params
  (:use ring.adapter.jetty
        ring.middleware.params
        ring.util.response))

(defn page
  [firstname lastname]
  (str "<html><body>"
       (if (and firstname lastname)
         (str "欢迎你丫！ " firstname " " lastname "!")
         (str "<form>"
              "First Name: <input name='firstname' type='text'>"
              "Last Name: <input name='lastname' type='text'>"
              "<input type ='submit'>"
              "</form>"))
       "</body></html>"))

(defn handler
  [{{firstname "firstname" lastname "lastname"} :params}];this destructure is really cool!
  (-> (response (page firstname lastname))
      (content-type "text/html")
      (charset "utf-8")))

;(handler {:params {"name" "Yuan Xue"}})
(def app
  (-> handler wrap-params))

(def server (run-jetty #'app {:port 9090 :join? false}))

(.stop server)

(page "" nil)
