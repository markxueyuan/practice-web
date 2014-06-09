(ns practice-web.template
  (:require [net.cgrand.enlive-html :as h]))


;sniptest is a utility provided by Enlive that simplifies experimenting with
;and transforming snippets of HTML in the REPL.

(h/sniptest "<h1>Lorem Ipsum</h1>")

;with transformation

(h/sniptest "<h1>Lorem Ipsum</h1>"
            [:h1] (h/content "Hello Reader!"))

(h/html-snippet "<p>x, <a id=\"home\" href=\"/\">y</a>, <a href=\"..\">z</a></p>")

(h/sniptest "<p>x, <a id=\"home\" href=\"/\">y</a>, <a href=\"..\">z</a></p>"
            [:a#home] (h/set-attr :href "http://clojurebook.com")
            [[:a (h/attr= :href "..")]] (h/content "go up"))

(h/sniptest "<span class=\"xiaoxiong\">hehehehe</span>"
            [:span.xiaoxiong] (h/set-attr :class "xiaogou"))

(h/sniptest "<p class=\"\"><a href=\"\" class=\"\"></a></p>"
            [[:p (h/attr? :class)]] (h/content "我是宇宙之王！"))

(h/sniptest "<p class=\"\"><a href=\"\" class=\"\"></a></p>"
            [:p (h/attr? :class)] (h/content "我是宇宙之王！"))



;to summarize: sets denote disjunction,
;inner vectors denote conjunction,
;outermost vectors denote hierarchical chaining.

;;;;;;;;;;;;;pred and zip-pred HOFs;;;;;;;;;;;;;;;;;


(defn some-attr=
  "Selector step, matches elements where at least one attribute has the specified value."
  [value]
  (h/pred (fn [node]
            (some #{value} (vals (:attrs node))))))

(h/sniptest "<ul><li id=\"foo\">A<li>B<li name=\"foo\">C</li></ul>"
            [(some-attr= "foo")] (h/set-attr :found "yes"))
;;;;;;;;;;;;;;;;;;;;;;;condition;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn display
  [msg]
  (h/sniptest "<div><span class=\"msg\"></span></div>"
              [:.msg] (when msg (h/content msg))))

(display "hehe")

(display nil)

(defn display
  [msg]
  (h/sniptest "<div><span class=\"msg\"></span></div>"
              [:.msg] (if msg
                        (h/content msg)
                        (h/add-class "hidden"))))

(display nil)

(display "hehe")


;;;;;;;;;;;;;;;;;;;;;iterating;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;clone-for behaves a lot like for

(defn countdown
  [n]
  (h/sniptest "<ul><li></li></ul>"
              [:li] (h/clone-for [i (range n 0 -1)]
                       (h/content (str i)))))

(countdown 10)
(countdown 0)

;do-> composes transformations by applying them in sequence

(defn countdown
  [n]
  (h/sniptest "<ul><li id=\"foo\"></li></ul>"
              [:#foo] (h/do->
                       (h/remove-attr :id)
                       (h/clone-for [i (range n 0 -1)]
                           (h/content (str i))))))

(countdown 3)


;;;;;;;;;;;;;;;;;;;;;;;;;;;defsnippet;;;;;;;;;;;;;;;;

(h/defsnippet footer (java.io.File. "footer.html") [:.footer];selector indicating the root element within the loaded file.
  [message]
  [:.footer] (h/content message))

(footer "hello")


;;;;;;;;;;;;;;;;;;;;;;;;;;;deftemplate;;;;;;;;;;;;;;;;;

(h/deftemplate friends-list (java.io.File. "friends.html")
  [username & friends]
  [:.username] (h/content username)
  [:ul.friends :li] (h/clone-for [f friends]
                 (h/content f)))

(apply str (friends-list "Mark" "Xiao Wang" "Xiao Li"))

(h/deftemplate friends-list (java.io.File. "friends.html")
  [username friends friend-class]
  [:.username] (h/content username)
  [:ul.friends :li] (h/clone-for [f friends]
                       (h/do-> (h/content f)
                               (h/add-class friend-class)))
  [:body] (h/append (footer (str "Good bye, " username))))

(apply str (friends-list "Mark" ["Xiao Wang" "Xiao Li"] "economist"))



























;;;;;;;;;;;;;;;;;;;;;;tips;;;;;;;;;;;;;;;;;;;;;;;;;;;

(range 10 0 -1)

(range 0 0 -1)
