(ns oseri.core)
; (ns oseri.core
;   (:use
;     oseri.view
;     oseri.model))

; (defn on-command
;   [cmdline]
;   (let [
;         cmd (first cmdline)
;         pos (second cmdline)
;       ]
;     (cond
;       (= cmd :move) (play-move pos)
;       (= cmd :exit) (System/exit 0)
;       :else nil)
;   ))

; (defn -main
;   [& args]
;   (init-view on-command)
;   (init-game on-state-changed)
;   (start-ui))
; (ns oser.core)

(defn add
  [n]
  (* n n n))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [
    n (add 100)
    s "World..."]
    (println "Hello!!!" s n args)))

; (defn -main
;   [& args]
;   (let two (add 1))
;   (println two))