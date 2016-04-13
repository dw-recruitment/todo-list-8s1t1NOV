(ns todo-list.task-handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
			[ring.util.response :as response]
  			[hiccup.core :refer :all ]
  			[hiccup.form :refer :all ]
  			[hiccup.element :refer :all ]
  			[clojure.set :as set ]
  			[todo-list.task :as task]
  			[todo-list.list :as list]
  			[todo-list.list-handler :as list-handler]))

;; TODO remove tha tweird xx yy dd stuff
(defn render-task [list-id task]
	(str
		(html [:li 
			(if (:complete task)
				(str "<del>" (:name task) "</del> " 
					(html (submit-button {:name (:id task) :value "todo" :style "width:100px;" } "xx")) 
					"</div>") 
				(str (:name task) " " 
					(html (submit-button {:name (:id task) :value "complete" :style "width:100px"} "yy" )) ))
			(html (submit-button {:name  (:id task) :value "delete" } "dd" )) ]) ))

(defn render-task-list [tasks list-id] 
		(form-to [:post "/form-handler"]
			(html [:ul 
					(reduce str (map (partial render-task list-id) tasks))] 
				(hidden-field "show-list" list-id) )))

(defn render-new-task-form [list-id] 
	(form-to [:post "/form-handler"]
		(html 
			(text-field   "task-name" )
			(hidden-field "show-list" list-id)
			(submit-button {:name "new-task"} "Create New Task" )) ))

(defn render-page [list-number]
	(response/header 
		(response/response
			(html 
				[:head
					[:title "Tasks"] ]
				[:body
					[:h1 "Tasks"]
						(list-handler/render-list-header (list/get-list-name list-number))
						(render-task-list (task/get-task-list list-number) list-number) 
						(render-new-task-form list-number) 
					[:h2 "Lists"]
						(list-handler/render-list-list (list/get-list-list) list-number)
						(list-handler/render-new-list-form list-number) ]))
		"Content-Type" "text/html")) 

(defn do-params 
"returns list to render, usually just a pass-through, but create-new-list"
[params]
	(let [inverted-params (set/map-invert params)
		  list-number (Integer/parseInt (or (params "show-list")  "1"))]
		(if (inverted-params "delete")
			(task/delete-task (Integer/parseInt (inverted-params "delete")) list-number))	
		(if (inverted-params "todo")
			(task/mark-todo (Integer/parseInt (inverted-params "todo")) list-number))	
		(if (inverted-params "complete")
			(task/mark-complete (Integer/parseInt (inverted-params "complete")) list-number))
		(if (params "new-task")
			(task/create-new-task (params "task-name") list-number))
		(cond 
			(params "new-list") (list/create-new-list (params "list-name"))
		    :else list-number)))


(defn handler [request-map]
	(println (:params request-map))
	(render-page (do-params (:params request-map))))





