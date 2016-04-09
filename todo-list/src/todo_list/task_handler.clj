(ns todo-list.task-handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
			[ring.util.response :as response]
  			[hiccup.core :refer :all ]
  			[hiccup.form :refer :all ]
  			[hiccup.element :refer :all ]
  			[todo-list.task :as task]))

(defn render-task [task]
	(html [:li 
			(str (:name task) " ") 
			(if (:complete task)
				"completed"
				(submit-button {:name "complete" :value (:id task) } "complete" )) ]))

(defn render-task-list [tasks] 
	(form-to [:post "/mark-complete-form"]
		(html [:ul 
			(reduce str (map render-task tasks))] )))

(defn render-new-task-form [] 
	(form-to [:post "/new-task-form"]
		(html 
			(text-field   "task-name" )
			(submit-button {:name "new-task"} "Create New Task" )) ))

(defn render_page []
	(response/header 
		(response/response
			(html 
				[:head
					[:title "Tasks"] ]
				[:body
					[:h1 "Tasks"]
						(render-task-list (task/get-task-list)) 
						(render-new-task-form) ]))
		"Content-Type" "text/html")) 

(defn do_params [params] 
  (if (params "complete") 
		(task/mark-complete (Integer/parseInt (params "complete"))))	
  (if (params "new-task")
		(task/create-new-task (params "task-name"))))

(defn handler [request-map]
	(do_params (:params request-map))
	(render_page))
