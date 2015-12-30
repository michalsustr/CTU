-module(client).
-export([loop/2, initial_state/2]).

-include_lib("./defs.hrl").

%%%%%%%%%%%%%%%
%%%% Connect
%%%%%%%%%%%%%%%
loop(St, {connect, _Server}) ->
    % Figure out if there is already a maintained connection
    case(is_connected(St)) of
        true -> {{error, user_already_connected, "You are already connected to a server, please disconnect first."}, St};
        false ->
            % Get server PID and save it
            ServerPid = whereis(list_to_atom(_Server)),

            case catch(genserver:request(ServerPid , {connect, St#cl_st.nick})) of
                % defined by our protocol
                ok ->
                    {ok, St#cl_st{serverPid = ServerPid}};
                {error, user_already_connected} ->
                    {{error, user_already_connected, "There is a user with this nick, please change it first"}, St};
                % server not reached
                {'EXIT', Reason} ->
                    {{error, server_not_reached, "Server could not be reached."}, St}
              end
    end ;

%%%%%%%%%%%%%%%
%%%% Disconnect
%%%%%%%%%%%%%%%
loop(St, disconnect) ->
    case(is_connected(St)) of
        false -> {{error, user_not_connected, "Cannot disconnect: you are not connected."}, St};
        true ->
             case(is_in_channel(St)) of
                  true -> {{error, leave_channels_first, "Leave channels before disconnecting!"}, St};
                  false ->
                      case catch(genserver:request(St#cl_st.serverPid, {disconnect, St#cl_st.nick})) of
                      % defined by our protocol
                        ok ->
                          {ok, St#cl_st{serverPid = empty}};
                      % server not reached
                        {'EXIT', _} ->
                          {{error, server_not_reached, "Server could not be reached."}, St}
                      end
             end
    end ;

%%%%%%%%%%%%%%
%%% Join
%%%%%%%%%%%%%%
loop(St,{join, ChannelFull}) ->
    case(is_connected(St)) of
      true ->
          case catch(genserver:request(St#cl_st.serverPid, {join, self(), St#cl_st.nick, ChannelFull})) of
          % defined by our protocol
            ok ->
              UpdateChannels = St#cl_st.channels ++ [list_to_atom(ChannelFull)],
              {ok, St#cl_st{ channels = UpdateChannels}};
            {error, user_already_joined} ->
              {{error, user_already_joined, "You already joined this channel"}, St};
          % server not reached
            {error, server_not_reached} ->
              {{error, server_not_reached, "Channel could not be reached."}, St};
            {'EXIT', Reason} ->
              {{error, server_not_reached, "Server could not be reached."}, St}
          end;
      false ->
        {{error, user_not_connected, "You must connect first."}, St}
    end;

%%%%%%%%%%%%%%%
%%%% Leave
%%%%%%%%%%%%%%%
loop(St, {leave, ChannelFull}) ->
  case(is_connected(St)) of
    true ->
        case catch(genserver:request(  whereis(list_to_atom(ChannelFull)), {leave, St#cl_st.nick})) of
        % defined by our protocol
          ok ->
            UpdateChannels = St#cl_st.channels -- [list_to_atom(ChannelFull)],
            {ok, St#cl_st{ channels = UpdateChannels}};
          {error, user_not_joined} ->
            {{error, user_not_joined, "You are not in this channel"}, St};
        % server not reached
          {'EXIT', _} ->
            {{error, server_not_reached, "Server could not be reached."}, St}
        end;
    false ->
        {{error, user_not_connected, "You must connect first."}, St}
  end;

%%%%%%%%%%%%%%%%%%%%%
%%% Sending messages
%%%%%%%%%%%%%%%%%%%%%
loop(St, {msg_from_GUI, ChannelFull, _Msg}) ->
  case catch(genserver:request(  whereis(list_to_atom(ChannelFull)), {message, St#cl_st.nick, _Msg})) of
  % defined by our protocol
    ok ->
      {ok, St};
    {error, user_not_joined} ->
      {{error, user_not_joined, "You are not in this channel"}, St};
  % server not reached
    {'EXIT', _} ->
      {{error, server_not_reached, "Server could not be reached."}, St}
  end;

%%%%%%%%%%%%%%
%%% WhoIam
%%%%%%%%%%%%%%
loop(St, whoiam) ->
    {St#cl_st.nick, St} ;

%%%%%%%%%%
%%% Nick
%%%%%%%%%%
loop(St,{nick,_Nick}) ->
  case(is_connected(St)) of
    true -> {{error, user_already_connected, "Disconnect before changing nick."}, St};
    false ->
      {ok, St#cl_st{nick=_Nick}}
  end;

%%%%%%%%%%%%%
%%% Debug
%%%%%%%%%%%%%
loop(St, debug) ->
    {St, St} ;

%%%%%%%%%%%%%%%%%%%%%
%%%% Incoming message
%%%%%%%%%%%%%%%%%%%%%
loop(St = #cl_st { gui = GUIName }, MsgFromClient) ->
    {ChannelFull, Name, Msg} = MsgFromClient,
    gen_server:call(list_to_atom(GUIName), {msg_to_GUI, ChannelFull, Name++"> "++Msg}),
    {ok, St}.

initial_state(Nick, GUIName) ->
    #cl_st { gui = GUIName, nick = Nick, serverPid = empty, channels = []}.

is_connected(#cl_st{serverPid = empty}) -> false;
is_connected(#cl_st{serverPid = _}) -> true.

is_in_channel(#cl_st{channels = []}) -> false;
is_in_channel(#cl_st{channels = _}) -> true.