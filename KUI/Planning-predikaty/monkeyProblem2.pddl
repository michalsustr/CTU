;; A very simple classical STRIPS domain
;; by Radek Marik, 2011.04.18

(define (problem MONKEY1)
  (:domain MONKEY)
  (:objects monkeyJudy - monkey 
            bananas kiwi - fruit
            boxA - box
            locX locY locZ  - location)
  (:init (and
    (onFloor monkeyJudy)
    (atM monkeyJudy locX)
    (atB boxA locY)
    (atF bananas locZ)
    (atF kiwi locX)
    (isClear boxA)
    )
  )
  
  (:goal (and 
    (hasFruit monkeyJudy bananas)
    (hasFruit monkeyJudy kiwi)
  ))
  

)
