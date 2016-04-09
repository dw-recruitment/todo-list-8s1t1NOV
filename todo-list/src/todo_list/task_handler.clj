(ns todo-list.task-handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
			[ring.util.response :as response]
  			[hiccup.core :refer :all ]
  			[hiccup.form :refer :all ]
  			[hiccup.element :refer :all ]
  			[clojure.set :as set ]
  			[todo-list.task :as task]))

(defn render-task [task]
	(html [:li 
			(if (:complete task)
				(str 
					"<del>" (:name task) "</del> " 
					(html (submit-button {:name (:id task) :value "todo" :style "width:100px;" } "Todo")) 
					"</div>"
				) 
				(str 
					(:name task) " " 
					(html (submit-button {:name (:id task) :value "complete" :style "width:100px"} "Complete")) 
				))
			(html (submit-button {:name  (:id task) :value "delete" } "Delete")) ]))

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
    ;; nice idiom, but I need the value, I mean key too 
    ;; (println (vals params) (some #{"delete"} (vals params)))

	(let [inverted-params (set/map-invert params)]
		(if (inverted-params "delete")
			(task/delete-task (Integer/parseInt (inverted-params "delete"))) )	
		(if (inverted-params "todo")
			(task/mark-todo (Integer/parseInt (inverted-params "todo"))) )	
		(if (inverted-params "complete")
			(task/mark-complete (Integer/parseInt (inverted-params "complete"))) ))	
	(if (params "new-task")
		(task/create-new-task (params "task-name"))))

(defn handler [request-map]
	(do_params (:params request-map))
	(render_page))
