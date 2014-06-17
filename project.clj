(defproject practice-web "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [lein-light-nrepl "0.0.10"]
                 [ring "1.3.0"]
                 [compojure "1.1.8"]
                 [enlive "1.1.5"]
                 [clout "1.2.0"]
                 [ring-mock "0.1.5"]]
  ;:plugins [[lein-ring "0.8.10"]]
  ;:ring {:handler your-app.core/handler}
  :repl-options {:nrepl-middleware [lighttable.nrepl.handler/lighttable-ops]}
  :jvm-opts ["-Xmx1g"])
