(defproject todo-list "0.1.0-SNAPSHOT"
  :description "DemocracyWorks Project - Anonymous Candidate - April 2016"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [compojure "1.5.0"]
                 [ring/ring-defaults "0.1.5"]
				 [hiccup "1.0.5"]
				 [org.clojure/java.jdbc "0.4.1"]
				 [org.postgresql/postgresql "9.4-1201-jdbc41"]]
  :plugins [[lein-ring "0.9.7"]]
  :ring {:handler todo-list.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.0"]] }})
