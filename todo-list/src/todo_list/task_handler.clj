(ns todo-list.task-handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
			[ring.util.response :as response]
  			[hiccup.core :refer :all ]
  			[todo-list.task :as task]))

(defn render-task [task]
	(html [:li (str  (:name task) "  " (:complete task))]))

(defn render-task-list [tasks] 
	(html [:ul 
		(reduce str (map render-task tasks))] ))

(defn handler [request-map]
	(response/header 
		(response/response
			(html 
				[:head
					[:title "Tasks"] ]
				[:body
					[:h1 "Tasks"]
					(render-task-list (task/get-task-list)) ] ))
		"Content-Type" "text/html")) 
