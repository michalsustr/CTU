load ('czechRepublic.mat');
alpha = 250;
beta = 1;
limit = 100; % limit of next nodes to include in the computation of error

% precompute the values for the k values
a = -repmat(y',length(y),1) + repmat(y,1,length(y));
b = repmat(x',length(x),1) - repmat(x,1,length(x));
U = -a.*repmat(x',length(y),1) - b.*repmat(y',length(x),1);

c = Inf(length(x));
for i = 1:length(x)
    % overflow check
    if ((i+limit) > length(x))
        offset = (i+limit) - length(x);
    else 
        offset = 0;
    end
    
    % loop until the limit
    for j = (i+2):(i+limit - offset)
        c(i,j) = alpha + beta*abs(sum(                                  ...
            (a(i,j))*x((i+1):(j-1)) + b(i,j)*y((i+1):(j-1)) + U(i,j)    ...
        ))/sqrt(a(i,j)^2 + b(i,j)^2);
    end
end

% dijkstra
N = size(c,1);
U = inf(1,N);
U(1) = 0;
previous = zeros(N,1);
next = inf(N,1);
replacement = zeros(1,N);
for i=1:N
    next = inf(N,1);
    next(previous == 0) = U(previous == 0);
    [~,id] = min(next);
    previous(id) = id;
    P = (c(id,:)+U(id));     
    replacement(P<U) = id;
    U(P<U) = P(P<U); 
end

% find the route
route = N;
i = N;
while (i ~= 0)
    route = [replacement(i) route];
    i = replacement(i);
end
route = route(2:end)

% show the graph
step = 20;
figure;
axis equal;
hold on;
plot(x(1:step:size(x,1)), y(1:step:size(y,1)),'x-');
plot(x(route),y(route),'r+--');
hold off;
