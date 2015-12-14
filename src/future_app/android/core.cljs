(ns ^:figwheel-load future-app.android.core
  (:require-macros [env.require-img :refer [require-img]])
  (:require [reagent.core :as r :refer [atom]]
            [re-frame.core :refer [subscribe dispatch dispatch-sync]]
            [future-app.handlers]
            [future-app.subs]))

(set! js/React (js/require "react-native/Libraries/react-native/react-native.js"))

(def app-registry (.-AppRegistry js/React))
(def text (r/adapt-react-class (.-Text js/React)))
(def view (r/adapt-react-class (.-View js/React)))
(def image (r/adapt-react-class (.-Image js/React)))
(def touchable-highlight (r/adapt-react-class (.-TouchableHighlight js/React)))

(def logo-img (require-img "./images/cljs.png"))

(defn widget []
  (let [greeting (subscribe [:get-greeting])]
    (fn []
      [view {:style {:flexDirection "column" :margin 40 :alignItems "center"}}
       [text {:style {:fontSize 30 :fontWeight "100" :marginBottom 20 :textAlign "center"}} @greeting]
       [image {:source logo-img
               :style  {:width 80 :height 80 :marginBottom 30}}]
       [touchable-highlight {:style {:backgroundColor "#999" :padding 10 :borderRadius 5}}
        [text {:style {:color "white" :textAlign "center" :fontWeight "bold"}} "press me"]]])))

(defn mount-root []
      (r/render [widget] 1))

(defn ^:export init []
      (dispatch-sync [:initialize-db])
      (.registerRunnable app-registry "FutureApp" #(mount-root)))
