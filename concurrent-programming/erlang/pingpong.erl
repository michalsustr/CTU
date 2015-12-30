-module(pingpong).

-export([start/0, pingueur/1, pongueur/1]).

%% Fonction qui va lancer nos deux processus avec le nombre initial
start() ->
    PID = spawn(?MODULE, pongueur, [0]),
    spawn(?MODULE, pingueur, [PID]). 
    
pingueur(PID) -> 
    PID ! {ping, self()},  
    receive 
        {pong, N} ->
            io:format("Reçu ~w~n", [N])
    end,
    pingueur(PID).  %% On boucle
    
pongueur(N) ->
    timer:sleep(2000),   %% Sert à attendre deux secondes (pour que les choses n'aillent pas trop vite)
    receive
        {ping, PID} ->   %% Un ping envoyé par PID
            M = N + 1,   %% Un message de plus
            PID ! {pong, M},
            pongueur(M)  %% On boucle avec le nouveau nombre de messages reçus.
    after
        5000 -> io:format("Je ne reçois rien, donc je m'arrête.~n")
    end.
