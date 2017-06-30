(ns oseri.core
  (:use
   oseri.view))
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

(defn -main
  [& args]
  (println (show-board (board 8))))
