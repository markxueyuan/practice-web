(ns practice-web.learn-hiccup
  (:use hiccup.core))

;Hiccup is a library for turning a Clojure data structure into a string of HTML.

(html [:span {:class "foo"} "bar"])

(html [:script])

(html [:p])

(html [:div#foo.bar.baz "bang"])

(html [:ul
       (for [x (range 1 4)]
         [:li x])])

(html [:a {:href "http://www.sohu.com"} "hahahah"])
