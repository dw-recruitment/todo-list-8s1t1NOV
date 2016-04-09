(ns todo-list.hello-gif-handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
			[ring.util.response :as response])
)

(defn handler [request-map]
	(response/header 
		(response/response
			(str "<html><body>"
				 "<img src=\"images/const_e0.gif\"/>"
				 "</body></html>"))
		"Content-Type" "text/html"))
