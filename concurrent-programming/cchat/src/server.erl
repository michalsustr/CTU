-module(server).
-export([loop/2, initial_state/1]).

-include_lib("./defs.hrl").

loop(St, {connect, ClientNick}) ->
  case lists:member(list_to_atom(ClientNick), St#server_st.nicks) of
      % Nick wasn't found in connected clients, so we can add this connection
      false ->
          UpdateNicks = St#server_st.nicks ++ [list_to_atom(ClientNick)],
          {ok, St#server_st{nicks = UpdateNicks}};
      % It was found, therefore raise error
      true ->
          % User already connected
          {{error, user_already_connected}, St}

  end;

loop(St, {disconnect, ClientNick}) ->
  case lists:member(list_to_atom(ClientNick), St#server_st.nicks) of
      false ->
          {{error,  user_not_connected}, St};
      true ->
          UpdateNicks = St#server_st.nicks -- [list_to_atom(ClientNick)],
          {ok, St#server_st{nicks = UpdateNicks}}
  end;

loop(St, {join, ClientPid, ClientNick, ChannelFull}) ->
  ChannelPid = whereis(list_to_atom(ChannelFull)),
  case ChannelPid of
  % It was not found, the channel does not exist therefore create new channel
    undefined ->
      genserver:start(list_to_atom(ChannelFull), channel:initial_state(ChannelFull),
          fun channel:loop/2);
    _ ->
      1+1
  end,

  ChannelPid2 = whereis(list_to_atom(ChannelFull)),
  case catch(genserver:request(ChannelPid2, {join, ClientNick, ClientPid})) of
    % defined by our protocol
    ok ->
        {ok, St};
    {error, user_already_joined} ->
        {{error, user_already_joined}, St};
    % channel server not reached
    {'EXIT', _} ->
        {{error, server_not_reached}, St}
    end.

initial_state(_Server) ->
    #server_st{nicks = []}.
