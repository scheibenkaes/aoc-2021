(ns aoc.day12
  (:require [aoc.core :as aoc]
            clojure.string))

(defn ->cave
  [name]
  {:name             (str name)
   :visit-again?     (= (clojure.string/upper-case name) name)
   :connected-to     #{}})

(defn ->connection
  [s]
  {:pre [(clojure.string/includes? s "-")]}
  (let [[start end] (clojure.string/split s #"-")]
   {:start start :end end}))

(defn all-names
  [lines]
  (set (mapcat #(clojure.string/split % #"-" 2) lines)))

(defn create-cave-map
  ""
  [lines]
  (let [names       (all-names lines)
        connections (map ->connection  lines)
        update-cave (fn [cave]
                      (let [froms (map :start (filter #(= (:name cave) (:end %)) connections))
                            tos   (map :end (filter #(= (:name cave) (:start %)) connections))]
                        (-> cave
                            (assoc :connected-to (set (concat froms tos))))))
        caves       (->> names
                         (map ->cave)
                         (map update-cave))]
    {:names       names
     :connections connections
     :caves       caves}))

(defn paths-leading-from
  [cmap point]
  (->> cmap
       :caves
       (filter #((:connected-to %) point))))

(defn visit-again?
  [already-visited cave]
  (or (:visit-again? cave)
      (not (already-visited (:name cave)))))

(defn map-res
  ""
  [res]
  (identity res))

(defn find-paths
  [cmap start & {:keys [already-visited path paths-found]
                 :or   {already-visited #{} path [] paths-found []}
                 :as   context}]
  (if (or
       (= start "end")
       (nil? start))
    (conj path start)
    (let [path*          (conj path start)
          possible-paths (paths-leading-from cmap start)
          possible-paths (filter #(visit-again? already-visited %) possible-paths)]
      (if-not possible-paths
        (recur cmap nil {:path path* :already-visited (conj already-visited start)})
        (for [next-step possible-paths]
          (find-paths
           cmap
           (:name next-step)
           :path path* :already-visited (conj already-visited start)))))))
