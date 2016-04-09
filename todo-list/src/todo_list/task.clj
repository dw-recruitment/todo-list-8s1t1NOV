(ns todo-list.task
	(:require [clojure.java.jdbc :as jdbc]))

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
	(jdbc/db-do-commands db 
		(jdbc/drop-table-ddl :task)))

(defn create-task-table []
	(jdbc/db-do-commands db
		(jdbc/create-table-ddl :task
			[:id :serial  "PRIMARY KEY"]
			[:name "VARCHAR(20)"]
			[:complete :boolean])))

(defn insert-starter-data []
	(jdbc/insert! db 
		:task {:name "dishes" :complete false })	
	(jdbc/insert! db 
		:task {:name "cat poop" :complete true }) 
	(jdbc/insert! db
		:task {:name "homework" :complete false }) )

(defn do-over [] 
	(drop-task-table)
	(create-task-table)
	(insert-starter-data) )

(defn get-task-list []
	(jdbc/query db ["SELECT name, complete, id from task"]))

(defn mark-complete [id] 
	(jdbc/update! db :task {:complete true} ["id = ?" id ] ))
							
