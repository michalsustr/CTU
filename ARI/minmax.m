% t - cas (x-ova os)
% y - hodnota (y-ova os)
function vysledok=minmax(t,y) 
    % matica s vysledkami
    vysledok=[];

    for i=2:1:size(y,2)-1
        % hladanie maxima
        if y(i-1) < y(i) && y(i+1) < y(i)
            vysledok=[vysledok [t(i); y(i)]];
        end

        % hladanie minima
        if y(i-1) > y(i) && y(i+1) > y(i)
            vysledok=[vysledok [t(i); y(i)]];
        end
    end

    % otvori okno s grafmi
    figure(1);
    clf;            % vymaze stare grafy
    hold on;        % podrzi graf aby sa neprepisal
    plot(t,y);      % vykresli funkciu
    if ~isempty(vysledok) % ak sme nasli nejake extremy
        plot(vysledok(1,:), vysledok(2,:), 'ro'); % vykresli lokalne minima/maxima
    end
    hold off;
end