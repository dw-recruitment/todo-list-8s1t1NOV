(ns todo-list.task
	(:require [clojure.java.jdbc :as sql])
)

;;
;; TODO
;; corral the connection attribute
;; hang on to a connection, reuse
;; but really, use korma or something

(def dbname "democracy_works")
(def db {:classname "org.postgresql.Driver"
		 :subprotocol "postgresql"
		 :subname (str "//127.0.0.1:5432/" dbname)
	     :user "demoworks"
		 :password "cap10"
		})


(defn drop-task-table []
	(sql/db-do-commands db 
		(sql/drop-table-ddl :task)))

(defn create-task-table []
	(sql/db-do-commands db
		(sql/create-table-ddl :task
			[:id :serial  "PRIMARY KEY"]
			[:name "VARCHAR(20)"]
			[:complete :boolean])))

(defn insert-starter-data []
	(sql/insert! db 
		:task {:name "dishes" :complete false})	
	(sql/insert! db 
		:task {:name "cat poop" :complete true}) 
	(sql/insert! db
		:task {:name "homework" :complete false}) )

(defn get-task-list []
	(sql/query db ["SELECT name, complete from task"]))

