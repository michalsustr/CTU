function x=simplex(c,A,b)
    % testovanie rozmerov
    if(size(c,2) ~= 1)
       error('Zle zadany vektor c, nie je jednostlpcovy'); 
    end
    if(size(b,2) ~= 1)
        error('Zle zadany vektor b, nie je jednostlpcovy');
    end
    if(size(c,1) ~= size(A,2)) 
        error('Rozmery A a c si nekoresponduju.')
    end
    if(size(b,1) ~= size(A,1)) 
        error('Rozmery A a b si nekoresponduju.')
    end
    
    % stare hodnoty
    cx = c;
    Ax = A;
    bx = b;
    
    d = 0;
    iter = 0;
    
    while(1)
        [c,A,b,d]=simplex_iteration(c,A,b,d);
        disp(d);
        if(isequal(cx,c) && isequal(Ax,A) && isequal(bx,b)) % ziadna zmena oproti minulej iteracii, skoncili sme
            disp(sprintf('Pocet iteracii: %i', iter));
            % zistime hodnotu x
            disp(c);
            x=zeros(size(A,2), 1);
            std = simplex_stdbase(A);
            for(i=1:size(std,2))
                x(std(i)) = b(i);
            end
            break;
        else
            cx = c;
            Ax = A;
            bx = b;
            iter=iter+1;
        end
    end
    return;
end

function [c,A,b,d]=simplex_iteration(c,A,b,d)
%% kontrola vlastnosti simplexovej tabulky
    % kotrola ci b >= 0
    % ked sa jedna o stlpcovu maticu, tak (i) ju vezme celu, ked je to
    % riadkova tak ju vezme po prvkoch
    for i = b' 
        if(i < 0)
            error('Hodnota b < 0');
        end
    end
    
    % Hladame bazove stlpce (tj podmnozinu stlpcov A ktora tvori std. bazu)
    % Prechadzame prvy riadok, a ked najdeme 1 alebo 0, tak
    % lezieme do dalsich riadkov, a snazime sa potvrdit ci to je bazovy
    % stlpec alebo nie.
    
    std = simplex_stdbase(A);
    
    % testovanie - mame uplnu std bazu?
    if(min(std) == 0)
        error('std. baza nie je uplna');
    end
    
    % su hodnoty vektora c prisluchajuce bazam nulove?
    for(k = 1:size(std, 2)) 
        if(c(std(k)) ~= 0)
           error('hodnota pre stlpec %i nie je nulova', std(k));
        end
    end
    
%% samotna iteracia
    
    % najdeme stlpec s najvacsou zapornou hodnotou
    [val,j] = min(c);
    if(val < 0)
        col = A(:,j);
        % vynulujeme vsetky zaporne hodnoty
        col(col<0) = [0];
        col = b./col;
        % vyhodime vsetky nulove riadky
        col(col<=0) = [];
        % najdeme minimum (kvoli tomu sme nulove riadky vyhodili)
        [val,i] = min(col);
        pivot=A(i,j);
        % nenajdeme pivot iba vtedy, ak su vsetky hodnoty v stlpci nekladne
        % vtedy je uloha neomezena
        if(isinf(val))
            disp('neomezena uloha');
            return;
        end
        
        % upravy okolo pivotu: riadok podelime tak aby sa z pivotu stala
        % jednicka, a vsetko ostatne v jeho stlpci bolo nulove (vratane
        % vektoru c')
        A(i,:)=A(i,:)/pivot;
        b(i)=b(i)/pivot;
        for(p=1:size(A,1))
            if(p~=i)
                b(p) = b(p)-A(p,j)*b(i);
                A(p,:) = A(p,:)-A(p,j)*A(i,:);
            end
        end
        % kriterialna hodnota
        d=d-c(j)*b(i);
        % vektor c
        c=c';
        c=c-c(j)*A(i,:);
        c=c';
        
    else
        % nic neupravujeme, uz sme hotovi
    end
    
    % teraz by sme uz mali byt v dalsej std. bazi
end

function std=simplex_stdbase(A)
    % ukladame referencie na stlpce
    std = zeros(1, size(A,1)); % k-ta pozicia odpoveda k-tej std. bazi, tj
    % ak by A bolo [-3,1,1,1,0; 1,-1,1,0,1], tak v std budu ulozene hodnoty
    % [4,5] - stvrty a piaty stlpec odpovedaju std. bazi
    % ak by A bolo [-3,1,1,0,1; 1,-1,1,1,0], tak v std budu ulozene hodnotysize(A,2)
    % [5,4] - zmenena pozicia std. bazy
    
    for j = size(A,2):-1:1 % stlpec
        k = 0; % pozicia v std. bazi
        for i = 1:size(A,1)  % riadok        
           if(A(i,j) ~= 0 && A(i,j) ~= 1) break; end

           if(A(i,j) == 1)
               if(k == 0)
                   k = i;
               else 
                   % uz sme nasli v tomto stlpci jednotku, ked ju najdeme znova
                   % to znamena ze sa nejedna o stlpec std. bazy
                   break;
               end
           end
           
           if(i == size(A,1)) 
              if(k == 0) % cely nulovy stlpec
                  disp('cely nulovy stplec??');
              else
                  % nasli sme std bazu
                  if(std(k) == 0)
                    std(k) = j;
                  end
              end
           end
        end
    end
    
    std
end