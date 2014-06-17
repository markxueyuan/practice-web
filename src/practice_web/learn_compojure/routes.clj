(ns practice-web.learn-compojure.routes
  (:use ring.adapter.jetty
        ring.util.response
        [compojure.core :only [GET POST PUT defroutes]])
  (:require [ring.mock.request :as mock]))


;routes return ring handler functions

(def handler-1 (GET "/user/:id" [id]         ;[id] is the same as {{id :id} :params}
                    (str "<h1>Hello user " id "</h1>")))

(def request-1 (mock/request :get "/user/jama"))

request-1

(handler-1 request-1)

;you can capture the request map by

(def handler-2 (GET "/" request (str request)))

(def request-2 (mock/request :get "/"))

request-2

(handler-2 request-2)


;The return value is treated intelligently.
;In this case a string is returned,
;so it’s turned into a standard response

;The compojure.response/render multimethod deals with turning a response of an arbitrary type
;(String, map, File, etc) into a suitable response.
;It can be overriden to provide custom rendering of your own types.



;;;;;;;;;;;;;;;;;;;;;;;;;;;;;tips;;;;;;;;;;;;;;;;;;

(str {:a 2 :b 3})
