(ns todo-list.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [compojure.handler]
			[ring.util.response :as resp])
  (:use [todo-list.hello-gif-handler :only [handler]]))


(defroutes app-routes
  (GET "/" [] handler)
  (GET "/about" [] (resp/resource-response "about.html" {:root "public"}))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
	(compojure.core/routes app-routes))

