(ns todo-list.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [compojure.handler]
			[ring.util.response :as resp]
			[ring.middleware.params :as params]
  			[todo-list.task-handler :as task ]
  			[todo-list.hello-gif-handler :as gif ]))


(defroutes app-routes
  (GET "/" [] task/handler)
  (POST "/form-handler" [] task/handler)
  (GET "/hello" [] gif/handler)
  (GET "/about" [] (resp/resource-response "about.html" {:root "public"}))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
	(params/wrap-params app-routes))
	;;(compojure.core/routes app-routes)) ;; does not support parameters!

