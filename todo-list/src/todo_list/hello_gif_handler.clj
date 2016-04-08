(ns todo-list.hello-gif-handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
			[ring.util.response :as response])
)

(defn handler [request-map]
 	{ :status 200
	  :headers {"Content-Type" "text/html"}
	  :body (str "<html><body><h1>Hello<h1>\n"
				"<img src=\"daniel.jpg\"/>"
				"<img src=\"http://www.anon.com/pic2.jpg\"/>\n"
				"</body></html>")})

;; needs a content type
(defn handler-wtf [request-map]
	;;(response/response
	(ring.util.response/response
		(str "<html><body>Heloooo</body></html>")))
