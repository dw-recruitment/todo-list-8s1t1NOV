(ns todo-list.hiccup-test-handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
			[ring.util.response :as response])
  (:use [hiccup.core :all []])
)

(defn hiccup-test-handler [request-map]
	(response/header 
		(response/response
			(html 
				[:head
					[:title "test hiccup"] ]
				[:body
					[:h1 ".... testing ..."]
					"plain text" ] ))
		"Content-Type" "text/html")) 
