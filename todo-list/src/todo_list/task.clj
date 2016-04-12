(ns todo-list.task
	(:require [clojure.java.jdbc :as jdbc])
	(:require [todo-list.list :as list]))

;;
;; TODO
;; corral the connection attribute
;; hang on to a connection, reuse
;; but really, use korma or something
;; consider a text value for state, allowing others, e.g. "in-progress" 
;; consider a "deleted" state in it's own column to preserve history..
;; consider a rule that says you can't delete unless its done (...a where clause on the delete...)

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
			[:complete :boolean]
			[:list_id "INTEGER"])))

(defn insert-starter-data []
	(jdbc/insert! db 
		:list {:name "starter" :id 1 })	
	(jdbc/insert! db 
		:task {:name "dishes" :complete false :list_id 1 })	
	(jdbc/insert! db 
		:task {:name "cat box" :complete true :list_id 1}) 
	(jdbc/insert! db
		:task {:name "homework" :complete false :list_id 1}) 
	(jdbc/insert! db
		:task {:name "taxes" :complete false :list_id 1}) )

(defn do-over [] 
	(drop-task-table)
	(list/drop-list-table)
	(create-task-table)
	(list/create-list-table)
	(insert-starter-data) )

(defn get-task-list [list-id]
	(jdbc/query db ["SELECT name, complete, id from task where list_id = ? order by id" list-id]))

(defn mark-complete [id list_id] 
	(jdbc/update! db :task {:complete true} ["id = ? and list_id  = ?" id list_id ] ))

(defn mark-todo [id list_id] 
	(jdbc/update! db :task {:complete false} ["id = ? and list_id = ?" id list_id] ))

(defn create-new-task [name list_id]
	(jdbc/insert! db
		:task {:name name :complete false :list_id list_id}) )
						
(defn delete-task [id list_id] 
	(jdbc/delete! db :task ["id = ? and list_id = ? " id list_id]))
