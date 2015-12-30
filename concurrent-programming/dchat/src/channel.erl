-module(channel).
-export([loop/2, initial_state/1]).
-include_lib("./defs.hrl").

initial_state(ChannelFull) ->
  #channel{name = ChannelFull, users=[], nicks=[]}.

loop(St, {join, ClientNick, ClientPid}) ->
  % Search if the nick has already joined
  case lists:member(list_to_atom(ClientNick), St#channel.nicks) of
        % Nick wasn't found in connected clients, so we can add this connection
        false ->
            NewUser = #user{nick = ClientNick, pid=ClientPid},
            UpdateNicks = St#channel.nicks ++ [list_to_atom(ClientNick)],
            UpdateUsers = St#channel.users ++ [NewUser],
            {ok, St#channel{nicks = UpdateNicks, users = UpdateUsers}};
        % It was found, therefore raise error
        true ->
            % User already joined
            {{error, user_already_joined}, St}
    end;

loop(St, {leave, ClientNick}) ->
  % Search if the nick has already joined
  case lists:member(list_to_atom(ClientNick), St#channel.nicks) of
  % Nick was found in the list of connected users, therefore remove it
    true ->
      UpdateNicks = St#channel.nicks -- [list_to_atom(ClientNick)],
      UpdateUsers = lists:filtermap(
        fun(X) ->
          case X#user.nick of
            ClientNick -> false;
            _ -> true
          end
        end,
        St#channel.users
      ),

      {ok, St#channel{nicks = UpdateNicks, users = UpdateUsers}};
  % It was not found, therefore raise error
    false ->
      {{error, user_not_joined}, St}
  end;


loop(St, {message, ClientNick, Msg}) ->
  % Search if the nick has already joined
  case lists:member(list_to_atom(ClientNick), St#channel.nicks) of
    true ->
      lists:foreach(fun(User) ->
          case User#user.nick of
            ClientNick ->
              1+1; % return nothing
            _ ->
              spawn(fun() ->
                 genserver:request(User#user.pid, {St#channel.name, ClientNick, Msg})
              end)
          end
        end,
        St#channel.users),
      {ok, St};
    false ->
      {{error, user_not_joined}, St}
  end.