(ns todo-list.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [compojure.handler])
  (use todo-list.hello-gif-handler))


(defroutes app-routes
  (GET "/" [] handler)
  (GET "/hello" [] "Hello World")
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
	(compojure.core/routes
		app-routes))

