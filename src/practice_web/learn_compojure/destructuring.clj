(ns practice-web.learn-compojure.destructuring
  (:use ring.adapter.jetty
        ring.util.response
        [compojure.core :only [GET POST PUT defroutes context]])
  (:require [ring.mock.request :as mock]))

;compojure supports two sorts of destructuring

;1. the clojure kind, as the let special form
;2. the compojure specific kind

;capture the request map directly

(GET "/" request
     (str request))

;if a map or symbol supplied, clojure destructuring syntax will be used

((GET "/:foo" {{foo :foo} :params}
     (str "Foo = " foo))

(mock/request :get "/heihei"))

(mock/request :get "/heihei")

;nesting routes using context macro

(defroutes main-routes
  (context "/api" []
      (GET "/something" [] "...")
      (GET "/sth-else" [] "...")))


(defroutes main-routes
  (context ["/v/:num", :num #"[0-1]"] [num]
    (GET "/entry" [] ...)         ; matches /v/0/entry and /v/1/entry
    (GET "/something" [] ...)))   ; matches /v/0/something and /v/1/something

