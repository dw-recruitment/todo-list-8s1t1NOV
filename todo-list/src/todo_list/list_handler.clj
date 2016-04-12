(ns todo-list.list-handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
			[ring.util.response :as response]
  			[hiccup.core :refer :all ]
  			[hiccup.form :refer :all ]
  			[hiccup.element :refer :all ]
  			[clojure.set :as set ]
  			[todo-list.task :as task]))

(defn render-list-header [list-name]
	(html [:h2 (str "List: " list-name) ]))

(defn render-list [list-id list]
	(str 
		(html (radio-button 
				"show-list"  (= (:id list) list-id) (:id list )) 
			  (:name list) )
	    "\n"))

(defn render-list-list [lists list-id] 
 		(form-to [:post "/form-handler"]
			(html [:ul 
				(reduce str (map (partial render-list list-id) lists))] 
				(submit-button "show selected list"))))

(defn render-new-list-form [list-id] 
	(form-to [:post "/form-handler"]
		(html 
			(text-field   "list-name" )
			(hidden-field "show-list" list-id) 
			(submit-button {:name "new-list"} "Create New Todo List" )) ))

