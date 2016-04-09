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
				(submit-button {:name "complete" :value (:id task) } (:complete task))) ]))

(defn render-task-list [tasks] 
	(form-to [:post "/mark-complete-form"]
		(html [:ul 
			(reduce str (map render-task tasks))] )))

(defn do_params [params] 
  (if (params "complete") 
		(task/mark-complete (Integer/parseInt (params "complete"))))	
)

(defn render_page []
	(response/header 
		(response/response
			(html 
				[:head
					[:title "Tasks"] ]
				[:body
					[:h1 "Tasks"]
						(render-task-list (task/get-task-list)) ]))
		"Content-Type" "text/html")) 

(defn handler [request-map]
	(do_params (:params request-map))
	(render_page))
