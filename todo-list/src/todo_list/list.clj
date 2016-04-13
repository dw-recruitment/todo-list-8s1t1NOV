(ns todo-list.list
	(:require [clojure.java.jdbc :as jdbc]))

(def dbname "democracy_works")
(def db {:classname "org.postgresql.Driver"
		 :subprotocol "postgresql"
		 :subname (str "//127.0.0.1:5432/" dbname)
	     :user "demoworks"
		 :password "cap10"
		})


(defn drop-list-table []
	(jdbc/db-do-commands db 
		(jdbc/drop-table-ddl :list)))

(defn create-list-table []
	(jdbc/db-do-commands db
		(jdbc/create-table-ddl :list
			[:id :serial  "PRIMARY KEY"]
			[:name "VARCHAR(20)"])))

(defn get-list-list []
	(jdbc/query db ["SELECT name, id from list order by id"]))

(defn get-list-name [id]
	(:name  (first (jdbc/query db ["SELECT name from list where id = ?" id]))))

(defn create-new-list 
"returns id of new list"
[name]
	(:id (first (jdbc/insert! db :list {:name name }))) )
						
