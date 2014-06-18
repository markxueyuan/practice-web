(ns practice-web.learn-hiccup
  (:use hiccup.core)
  (:use hiccup.form))

;Hiccup is a library for turning a Clojure data structure into a string of HTML.

(html [:span {:class "foo"} "bar"])

(html [:script])

(html [:p])

(html [:div#foo.bar.baz "bang"])

(html [:ul
       (for [x (range 1 4)]
         [:li x])])

(html [:a {:href "http://www.sohu.com"} "hahahah"])

(html [:p "hello" [:em "world!"]])

;css-style sugar

(html [:span {:id "email" :class "selected starred"} "hahahah"])

;can be shortened as

(html [:span#email.selected.starred "hahahah"])

;the id must always come first
;so div#foo.bar would work, but div.foo#bar would not
;besides, you can only have one id, but you can have multiple classes

(html [:div "hello" "world"]) ;can be rewritten as

(html [:div (list "hello" "world")])

;that means, the seq is expanded out into the body

;this is extremely useful for things LinkageError

(html [:ul (for [x [1 2 3 4]] [:li x])])


(html (form-to [:post ""]
         [:fieldset.mds-border {:style "padding-top: 20px"}]))
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;tips;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;note that while lists are considered to be seqs by clojure, vectors and sets are not! Astonishing!

(seq? (list 1 2))
(seq? [1 2])
(seq? #{1 2})
