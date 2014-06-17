(ns practice-web.learn-clout
  (:require [ring.mock.request :as mock]
            [clout.core :as clout]))

;what does mock do

(mock/request :get "/article/clojure")

(clout/route-matches "/article/:title"
                     (mock/request :get "/article/clojure"))

(clout/route-matches "/public/*"
               (mock/request :get "/public/style/screen.css"))

;clout supports both keywords and wildcards

;keywords match any character except / . , ; ?, woldcards * matches anything

;if route does not match, nil is returned

(clout/route-matches "/product" (mock/request :get "/articles"))

(clout/route-matches "/:haha" (mock/request :get "/articles"))

;you can pre-compile a root

(def user-route (clout/route-compile "/user/:id"))

user-route

(clout/route-matches user-route (mock/request :get "/user/10"))

;you can specify a map of regular expressions for keywords, this makes routing more specific

(def user-route-2 (clout/route-compile "/user/:id" {:id #"\d+"}))

(clout/route-matches user-route-2 (mock/request :get "/user/10"))

(clout/route-matches user-route-2 (mock/request :get "/user/hello"))
