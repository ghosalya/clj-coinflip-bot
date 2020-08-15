(ns clj-coinflip-bot.core
  (:require [clojure.core.async :refer [<!!]]
            [clojure.string :as str]
            [environ.core :refer [env]]
            [morse.handlers :as h]
            [morse.polling :as p]
            [morse.api :as t]
            [clj-coinflip-bot.rng :as rng])
  (:gen-class))

; TODO: fill correct token
(def token (env :telegram-token))

(def help-message "The following commands are available:
/coin - flip a coin
/dice or /d6 - roll a 6-sided die
/d20 - roll a 20-sided die
/cowgames - suggests a game to play
/advise - get a recommendation from the system")


(h/defhandler handler

  (h/command-fn "start"
    (fn [{{id :id :as chat} :chat}]
      (println "Bot joined new chat: " chat)
      (t/send-text token id "Hi, I'm CLJ Coinbot! Try `/help` to start.")))

  (h/command-fn "help"
    (fn [{{id :id :as chat} :chat}]
      (println "Help was requested in " chat)
      (t/send-text token id help-message)))

  (h/command-fn "coin"
    (fn [{{id :id :as chat} :chat}]
      (println "Coin flipped in " chat)
      (t/send-text token id (str/join " " ["You flipped" (rng/flip-coin)]))))

  (h/command-fn "d6"
    (fn [{{id :id :as chat} :chat}]
      (println "Rolled a d6 in " chat)
      (t/send-text token id (str/join " " ["You rolled" (rng/roll-six)]))))

  (h/command-fn "d20"
    (fn [{{id :id :as chat} :chat}]
      (println "Rolled a d20 in " chat)
      (t/send-text token id (str/join " " ["You rolled" (rng/roll-twenty)]))))

  (h/command-fn "cowgames"
    (fn [{{id :id :as chat} :chat}]
      (println "Randomizing games for cows in " chat)
      (t/send-text token id (str/join " " ["Lets play:" (rng/random-gaming-cows)]))))

  (h/command-fn "advise"
    (fn [{{id :id :as chat} :chat}]
      (println "Giving random advise in " chat)
      (t/send-text token id (str/join " " ["The system recommends:" (rng/random-recommend)]))))

  (h/message-fn
    (fn [{{id :id} :chat :as message}]
      (println "Intercepted message: " message)
      (t/send-text token id "I don't do a whole lot ... yet."))))


(defn -main
  [& args]
  (when (str/blank? token)
    (println "Please provde token in TELEGRAM_TOKEN environment variable!")
    (System/exit 1))

  (println "Starting the clj-coinflip-bot")
  (<!! (p/start token handler)))
