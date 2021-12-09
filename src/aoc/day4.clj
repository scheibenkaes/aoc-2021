(ns aoc.day4
  (:require [aoc.core :as aoc]
            clojure.set))

(defn ->board
  ^{:pre [(= 25 (count nums))]}
  [nums]
  {:numbers           nums
   :winner?           false
   :type              ::board
   :last-call         nil
   :checked-nums      #{}
   :checked-positions #{}})

(defn load-board [data]
  (let [[nums-drawn & boards] (filter (complement empty?) data)
        nums-drawn            (read-string (str "[" nums-drawn "]"))
        boards                (read-string (str "[" (clojure.string/join " " boards) "]"))
        boards                (partition-all 25 boards)]
    {:numbers nums-drawn
     :boards  (map ->board boards)}))

(defn find-position [needle haystack]
  (first (keep-indexed #(when (= %2 needle) %1) haystack)))

(def checker (some-fn (partial clojure.set/subset? #{0  1  2  3  4})
                      (partial clojure.set/subset? #{5  6  7  8  9})
                      (partial clojure.set/subset? #{10 11 12 13 14})
                      (partial clojure.set/subset? #{15 16 17 18 19})
                      (partial clojure.set/subset? #{20 21 22 23 24})

                      (partial clojure.set/subset? #{0 5 10 15 20})
                      (partial clojure.set/subset? #{1 6 11 16 21})
                      (partial clojure.set/subset? #{2 7 12 17 22})
                      (partial clojure.set/subset? #{3 8 13 18 23})
                      (partial clojure.set/subset? #{4 9 14 19 24})))

(defn update-if-won
  [{:keys [checked-positions] :as board}]
  (let [won? (checker checked-positions)]
    (assoc board :winner? won?)))


(defn check-number
  [{:keys [numbers] :as board} number]
  (if-let [pos (find-position number numbers)]
    (let [board* (-> board
                     (update :checked-nums conj number)
                     (update :checked-positions conj pos)
                     (update-if-won)
                     (assoc :last-call number))]
      board*)
    board))

(defn winner-board
  [boards]
  (->> boards
       (filter :winner?)
       first))

(defn run-numbers
  ""
  [{:keys [numbers boards] :as game}]
  (if-not (empty? numbers)
    (let [number  (first numbers)
          boards* (map #(check-number % number) boards)
          winner? (winner-board boards*)]
      (if winner?
        (assoc game :boards boards* :winning-board winner?)
        (recur (assoc game
                      :numbers (rest numbers)
                      :boards boards*))))
    game))


(defn score-game
  ""
  [game]
  (when-let [winning-board (:winning-board game)]
    (let [{numbers         :numbers
           already-checked :checked-nums
           last-call       :last-call} winning-board
          unchecked                    (clojure.set/difference (set numbers) already-checked)
          unchecked                    (apply + unchecked)]
      (assoc game :score (* last-call unchecked)))))

(defn run-pt2
  ""
  [{:keys [numbers boards] :as game}]
  (if-not (empty? numbers)
    (let [number              (first numbers)
          boards*             (map #(check-number % number) boards)
          boards-which-won    (filter :winner? boards*)
          boards-not-won-prev (filter (complement :winner?) boards)
          count-won           (count boards-which-won)
          count-prev          (count boards-not-won-prev)
          number-of-boards    (count boards)
          all-won?            (= number-of-boards count-won)
          last-board          (fn []
                                (let [old (first boards-not-won-prev)]
                                  (some #(when (= (:numbers old) (:numbers %))
                                           %) boards*)))]
      (if all-won?
        (assoc game :boards boards* :winning-board (last-board))
        (recur (assoc game
                      :numbers (rest numbers)
                      :boards boards*))))
    game))
