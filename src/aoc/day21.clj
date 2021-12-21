(ns aoc.day21
  (:require [aoc.core :as aoc]))

(defn load-starting-pos
  [[p1 p2]]
  (letfn [(read-pos [l]
            (-> (re-find #"Player [0-9]{1} starting position: (.*)" l)
                second
                aoc/->int))]
    [(read-pos p1) (read-pos p2)]))

(defn ->player
  ""
  [start]
  start)

(defn ->board
  ""
  []
  (cycle (range 1 11)))

(defn ->dice
  ""
  []
  (let [nums (cycle (range 1 101))]
    {:nums         nums
     :times-rolled 0
     :roll         nil}))

(defn ->game
  ""
  [lines]
  (let [[p1 p2] (load-starting-pos lines)]
    {:player1 (->player p1)
     :player2 (->player p2)
     :score1  0
     :score2  0
     :board   (->board)
     :dice    (->dice)
     :winner  nil}))

(defn roll!
  [{:keys [nums times-rolled] :as dice}]
  (let [roll (first nums)]
    (assoc dice
           :roll roll
           :times-rolled (inc times-rolled)
           :nums (rest nums))))

(defn tick
  ""
  [{:keys [player1 player2 dice board] :as game}]
  (letfn [(score-player [game dice position score player]
            (let [board* (drop-while #(not= position %) board)
                  roll1  (roll! dice)
                  roll2  (roll! roll1)
                  roll3  (roll! roll2)
                  rolls  (map :roll [roll1 roll2 roll3])
                  rolls  (apply + rolls)
                  points (nth board* rolls)]
              (-> game
                  (update score + points)
                  (assoc player points)
                  (assoc :dice roll3))))]
    (let [game1 (score-player game dice player1 :score1 :player1)
          game2 (score-player game1 (:dice game1) player2 :score2 :player2)
          winner (cond
                   (>= (:score1 game1) 1000) :player1
                   (>= (:score2 game2) 1000) :player2
                   :else nil)]
      (case winner
        :player1 (assoc game1 :winner winner)
        (assoc game2 :winner winner)))))

(defn score-game
  [{:keys [winner score1 score2 dice]}]
  (when winner
    (println winner score1 score2 (:times-rolled dice))
    (let [mn    (min score1 score2)
          rolls (:times-rolled dice)]
      (* mn rolls))))
