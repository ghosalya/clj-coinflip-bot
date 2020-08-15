(ns clj-coinflip-bot.rng
  (:require [clojure.string :as str])
  (:gen-class))


(defn flip-coin []
    (rand-nth ["head" "tails"]))

(defn roll-dice [n]
    (+ 1 (rand-int n)))

(defn roll-six []
    (roll-dice 6))

(defn roll-twenty []
    (roll-dice 20))

(defn random-gaming-cows []
    (rand-nth [
            "Borderlands 3"
            "Stardew Valley"
            "Risk of Rain 2"
            "Destiny 2"
            "Jackbox"
            "Scribbl.io"
            "Towerfall"
            "Alien Swarm"
            "HTHT"
        ]
    ))


(defn random-recommend []
    (rand-nth [
            "git gud"
            "reach superior positioning"
            "follow the supreme leader"
            "blame fireteam leader"
            "chill, there's nothing to lose"
        ]
    ))
