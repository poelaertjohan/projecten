using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Identity;
using Microsoft.EntityFrameworkCore;
using Microsoft.AspNetCore.Hosting;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using BreakOutBox.Data;
using BreakOutBox.Models;
using BreakOutBox.Services;
//using EFGetStarted.AspNetCore.ExistingDb.Models;
using dotnet_g05.Data;
using Microsoft.AspNetCore.Http;
using BreakOutBox.Data.Repositories;
using BreakOutBox.Filters;
using BreakOutBox.Models.RepositoryInterfaces;

namespace BreakOutBox
{
    public class Startup
    {
        public Startup(IConfiguration configuration)
        {
            Configuration = configuration;
        }

        public IConfiguration Configuration { get; }

        // This method gets called by the runtime. Use this method to add services to the container.
        public void ConfigureServices(IServiceCollection services)
        {

            services.AddDbContext<ApplicationDbContext>(options =>
                options.UseSqlServer(Configuration.GetConnectionString("DefaultConnection")));

            services.AddIdentity<ApplicationUser, IdentityRole>()
                .AddEntityFrameworkStores<ApplicationDbContext>()
                .AddDefaultTokenProviders();

            // Add application services.
            services.AddTransient<IEmailSender, EmailSender>();
            services.AddSingleton<IHttpContextAccessor, HttpContextAccessor>();

            services.AddMvc().AddSessionStateTempDataProvider();
            services.AddSession();
            services.AddScoped<IOefeningRepository, OefeningRepository>();
            services.AddScoped<ISessieRepository, SessieRepository>();
            services.AddScoped<IGroepRepository, GroepRepository>();
            services.AddScoped<IVooruitgangRepository, VooruitgangRepository>();
            services.AddScoped<IBoxRepository, BoxRepository>();
            services.AddScoped<IGroepRepository, GroepRepository>();
            services.AddScoped<IMergeallRepository, MergeallRepository>();
            services.AddScoped<IGroepsBewerkingRepository, GroepsbewerkingRepository>();
            services.AddScoped<IActieRepository, ActieRepository>();
            services.AddScoped<IToegangscodeRepository, ToegangscodeRepository>();
            services.AddScoped<SessieFilter>();
            services.AddScoped<GroepFilter>();

          //  var connection = @"Data Source=DESKTOP-RKGG93M;Initial Catalog=BreakOutBoxDb;User ID=root;Password=root";
            //services.AddDbContext<BreakOutBoxDbContext>(options => options.UseSqlServer(connection));
        }

        // This method gets called by the runtime. Use this method to configure the HTTP request pipeline.
        public void Configure(IApplicationBuilder app, IHostingEnvironment env)
        {
            if (env.IsDevelopment())
            {
                app.UseDeveloperExceptionPage();
                app.UseBrowserLink();
                app.UseDatabaseErrorPage();
            }
            else
            {
                app.UseExceptionHandler(" / Home/Error");
            }
            app.UseSession();
            app.UseStatusCodePages();
            app.UseStaticFiles();

            app.UseAuthentication();

            app.UseMvc(routes =>
            {
                routes.MapRoute(
                    name: "default",
                    template: "{controller=Sessie}/{action=Index}");
            });
        }
    }
}
