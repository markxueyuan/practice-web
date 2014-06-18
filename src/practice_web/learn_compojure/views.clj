(ns practice-web.learn-compojure.views
  (:use [hiccup core page]))

(defn index-page
  []
  (html5
   [:head
    [:title "Hello World"]
    (include-css "/css/style.css")]
   [:body
    [:h1 "干你老母！"]]))
