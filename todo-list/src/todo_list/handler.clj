(ns todo-list.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [compojure.handler]
			[ring.util.response :as resp]
  			[todo-list.task-handler :as task ]
  			[todo-list.hello-gif-handler :as gif ]))


(defroutes app-routes
  (GET "/" [] task/handler)
  (GET "/hello" [] gif/handler)
  (GET "/about" [] (resp/resource-response "about.html" {:root "public"}))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
	(compojure.core/routes app-routes))

